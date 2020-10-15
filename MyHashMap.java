package Hash_Map;

import java.util.ArrayList;

public class MyHashMap<K,V> {
	public static void main(String[] args) {
		System.out.println("Welcome to the Hash Table Program.");
	}
	MyLinkedList<K,V> myLinkedList;
	public MyHashMap() {
		this.myLinkedList = new MyLinkedList<>();
	}
	public V get(K key) {
		MyMapNode<K, V> myMapNode = (MyMapNode<K,V>) this.myLinkedList.search(key);
		return (myMapNode == null) ? null : myMapNode.getValue();
	}
	public void add(K key, V value) {
		MyMapNode<K,V> myMapNode = (MyMapNode<K,V>) this.myLinkedList.search(key);
		if (myMapNode == null) {
			myMapNode = new MyMapNode<>(key, value);
			this.myLinkedList.append(myMapNode);
		} else {
			myMapNode.setValue(value);
		}
	}
	@Override
	public String toString() {
		return "MyHashMapNodes { " + myLinkedList + " }";
	}
}

interface INode<K,V> {
	K getKey();
	void setKey(K key);
	V getValue();
	void setValue(V value);
	INode<K,V> getNext();
	void setNext(INode<K,V> next);
}

class MyMapNode<K,V> implements INode<K,V> {
	K key;
	V value;
	MyMapNode<K,V> next;
	public MyMapNode(K key, V value) {
		this.key = key;
		this.value = value;
		next = null;
	}
	@Override
	public K getKey() {
		return key;
	}
	@Override
	public void setKey(K key) {
		this.key = key;
	}
	@Override
	public INode<K,V> getNext() {
		return next;
	}
	@Override
	public void setNext(INode<K,V> next) {
		this.next = (MyMapNode<K,V>) next;
	}
	@Override
	public V getValue() {
		return value;
	}
	@Override
	public void setValue(V value) {
		this.value = value;
	}
	@Override
	public String toString() {
		StringBuilder myMapNodeString = new StringBuilder();
		myMapNodeString.append(" MyMapNode { " + "K=").append(key).append(" V=").append(value).append(" } ");
		if (next != null) {
			myMapNodeString.append("->").append(next);
		}
		return myMapNodeString.toString();
	}
}

class MyLinkedList<K,V> {
	public INode<K,V> head;
	public INode<K,V> tail;
	public MyLinkedList() {
		this.head = null;
		this.tail = null;
	}
	public void add(INode<K,V> newNode) {
		if (this.tail == null) {
			this.tail = newNode;
		}
		if (this.head == null) {
			this.head = newNode;
		}
		else {
			INode<K,V> tempNode = this.head;
			this.head = newNode;
			this.head.setNext(tempNode);
		}
	}
	public void append(INode<K,V> newNode) {
		if (this.tail == null) {
			this.tail = newNode;
		}
		if (this.head == null) {
			this.head = newNode;
		}
		else {
			this.tail.setNext(newNode);
			this.tail = newNode;
		}
	}
	public void insert(INode<K,V> myNode, INode<K,V> newNode) {
		INode<K,V> tempNode = myNode.getNext();
		myNode.setNext(newNode);
		newNode.setNext(tempNode);
	}
	public INode<K,V> pop() {
		INode<K,V> tempNode = this.head;
		this.head = head.getNext();
		return tempNode;
	}
	public INode<K,V> popLast() {
		INode<K,V> tempNode = this.head;
		while (!tempNode.getNext().equals(tail))
		{
			tempNode = tempNode.getNext();
		}
		this.tail = tempNode;
		tempNode = tempNode.getNext();
		return tempNode;
	}
	public INode<K,V> search(K key) {
		INode<K,V> tempNode = this.head;
		while(tempNode != null && tempNode.getNext() != null)
		{
			if(tempNode.getKey().equals(key)) {
				return tempNode;
			}
			else {
				tempNode=tempNode.getNext();
			}
		}
		return null;
	}
	public INode<K,V> insertAfterNode(K key,INode<K,V> newNode) {
		INode<K,V> tempNode = this.head;
		while(tempNode != null && tempNode.getNext() != null)
		{
			if(tempNode.getKey().equals(key)) {
				return tempNode;
			}
			else {
				tempNode=tempNode.getNext();
			}
		}
		INode<K,V> newTempNode = tempNode.getNext();
		tempNode.setNext(newNode);
		newNode.setNext(newTempNode);
		return tempNode;
	}
	public void delete(INode<K,V> deleteNode) {
		INode<K,V> tempNode = this.head;
		INode<K,V> prevNode = null;
		while (tempNode != null && tempNode.getKey() != deleteNode.getKey())
		{
			prevNode = tempNode;
			tempNode = tempNode.getNext();
		}
		prevNode.setNext(tempNode.getNext());
		tempNode.setNext(null);
	}
	public int size() {
		int size = 0;
		INode<K,V> node = head;
		while (node != null)
		{
			size++;
			node = node.getNext();
		}
		return size;
	}
	public void printMyNodes() {
		StringBuffer myNodes = new StringBuffer("My Nodes: ");
		INode<K,V> tempNode = head;
		while (tempNode.getNext() != null)
		{
			myNodes.append(tempNode.getKey());
			if (!tempNode.equals(tail)) {
				myNodes.append("->");
			}
			tempNode = tempNode.getNext();
		}
		myNodes.append(tempNode.getKey());
		System.out.println(myNodes);
	}
	@Override
	public String toString() {
		return "MyLinkedList {" + head + "}";
	}
}

class MyLinkedHashMap<K,V> {
	private final int bucketSize;
	ArrayList<MyLinkedList<K,V>> myBucketArray;
	public MyLinkedHashMap() {
		this.bucketSize = 10;
		this.myBucketArray = new ArrayList<>(bucketSize);
		for (int index = 0 ; index < bucketSize ; index ++) {
			this.myBucketArray.add(null);
		}
	}
	public V get(K key) {
		int index = this.getBucketIndex(key);
		MyLinkedList<K,V> myLinkedList = this.myBucketArray.get(index);
		if (myLinkedList == null) {
			return null;
		} else {
			MyMapNode<K,V> myMapNode = (MyMapNode<K,V>) myLinkedList.search(key);
			return (myMapNode == null) ? null : myMapNode.getValue();
		}
	}
	private int getBucketIndex(K key) {
		int hashCode = Math.abs(key.hashCode());
		int index = hashCode % bucketSize;
		return index;
	}
	public void add(K key, V value) {
		int index = this.getBucketIndex(key);
		MyLinkedList<K,V> myLinkedList = this.myBucketArray.get(index);
		if (myLinkedList == null) {
			myLinkedList = new MyLinkedList<>();
			//MyMapNode<K,V> myMapNode = new MyMapNode<>(key, value);
			this.myBucketArray.set(index, myLinkedList);
		}
		MyMapNode<K,V> foundKey = (MyMapNode<K,V>) myLinkedList.search(key);
		if (foundKey == null) {
			foundKey = new MyMapNode<>(key, value);
			myLinkedList.append(foundKey);
		} else {
			foundKey.setValue(value);
		}
	}
	@Override
	public String toString() {
		return "MyLinkedHashMap List {" + myBucketArray + "}";
	}
}