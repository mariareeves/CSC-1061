import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;



public class FileTree implements Iterable <FileNode> {

	private FileNode root;
	
	public FileTree(String path) {
		root = new FileNode(path);
		buildTree(root);
	}

	/**
	 * Return a depth first post-order traversal iterator 
	 */
	@Override
	public Iterator<FileNode> iterator() {

		return new DepthFirstIterator();
	}
	
	/**
	 * 	TODO for Exam 2
	 *  Use recursion to build the tree from the directory structure.
	 *  For each of node starting from the root node use listFiles() from File to get 
	 *  the list of files in that directory/folder.
	 *  Create a node for each of the files and add it to a list of child nodes for the node
	 *  Do this recursively for all the nodes.  
	 * 
	 * @param fileNode
	 */
	private void buildTree(FileNode fileNode) {
		// stop if fileNode is empty or something went wrong
		if (fileNode == null) return;

		// get the actual file or folder from this node
		File f = fileNode.getFile();
		if (f == null) return;

		// if it's a normal file, nothing else to do (size already set)
		if (f.isFile()) {
			return;
		}

		// if it's a folder, get all files/folders inside it
		File[] kids = f.listFiles();
		if (kids != null) {
			// sort by name so the output is always in the same order
			java.util.Arrays.sort(kids, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

			// go through each file/folder inside
			for (File child : kids) {
				// skip .DS_Store and other dotfiles so output is cleaner
				if (child.getName().startsWith(".")) {
					continue;
				}

				// make a new node for the child
				FileNode childNode = new FileNode(child);
				// add this child node to the current node's list
				fileNode.getChildNodes().add(childNode);
				// call buildTree again for this child
				buildTree(childNode);
			}
		}

		// now that all children are built, add up their sizes
		long sum = 0L;
		for (FileNode childNode : fileNode.getChildNodes()) {
			sum += childNode.getFileSize();
		}

		// set the folder size as the total of all its children
		fileNode.setFileSize(sum);
	}



	/**
	 * TODO for Exam 2
	 * Iterator that does a post order traversal of the tree.
	 * For post-order traversal use the 2 stack approach outlined here: 
	 * https://www.geeksforgeeks.org/iterative-postorder-traversal/
	 * 
	 * @return 
	 */
	private class DepthFirstIterator implements Iterator<FileNode> {

		// we will use two stacks to visit the tree
		private Deque<FileNode> s1 = new ArrayDeque<>();  // first stack for processing
		private Deque<FileNode> s2 = new ArrayDeque<>();  // second stack stores final order

		public DepthFirstIterator() {
			// start by putting the root node in the first stack
			if (root != null) {
				s1.push(root);
			}

			// loop until we processed everything
			while (!s1.isEmpty()) {
				// take one node out of s1
				FileNode cur = s1.pop();
				// put it into s2 as this reverses the order later
				s2.push(cur);

				// go through all children of this node
				for (FileNode child : cur.getChildNodes()) {
					if (child != null) {
						// put each child in s1 to process later
						s1.push(child);
					}
				}
			}
			// now s2 has nodes in post-order, children first, then parent
		}

		@Override
		public boolean hasNext() {
			// as long as s2 is not empty, we still have nodes to visit
			return !s2.isEmpty();
		}

		@Override
		public FileNode next() {
			// take the next node out of s2
			return s2.pop();
		}
	}


	
	/**
	 *  Returns an iterator that does a breadth first traversal of the tree using a queue.
	 * 
	 * @return
	 */
	public Iterator<FileNode> breadthFirstIterator() {
		
		return new BreadthFirstIterator();

	}	
	
	/** 
	 * TODO for Exam 2
	 * Iterator that does a breadth first traversal of the tree using a queue.
	 * 
	 */

	private class BreadthFirstIterator implements Iterator<FileNode> {

		//  queue to visit the tree level by level
		private Queue<FileNode> q = new ArrayDeque<>();

		public BreadthFirstIterator() {
			// start by adding the root node into the queue
			if (root != null) {
				q.offer(root);
			}
		}

		@Override
		public boolean hasNext() {
			// return true only if there are still nodes in the queue
			return !q.isEmpty();
		}

		@Override
		public FileNode next() {
			// remove the next node from the front of the queue
			FileNode cur = q.poll();

			// add all of this node’s children to the back of the queue
			if (cur != null) {
				for (FileNode child : cur.getChildNodes()) {
					if (child != null) {
						q.offer(child);
					}
				}
			}

			// return the current node we just visited
			return cur;
		}
	}


}
