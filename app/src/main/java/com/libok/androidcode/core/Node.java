package com.libok.androidcode.core;

import java.util.List;

/**
 * @author liboK  2018/08/07 下午 06:12
 * 链表或树的节点
 */
public class Node<T> {
    /**
     * 当前节点的保存数据
     */
    private T data;
    /**
     * 当前节点标识
     */
    private String tag;
    /**
     * 根节点到当前节点路径
     */
    private String path;
    /**
     * 当前节点的父节点，如果当前是根节点那么此项为null
     */
    private Node parent;
    /**
     * 当前节点的子节点
     */
    private List<Node<T>> next;
//    private Node ;
//    private Node next;

    private int index;

    private String[] pathSplite;


    public Node(String tag) {
        this.tag = tag;
    }

    /**
     * @param data   当前节点的数据
     * @param tag    当前节点的tag
     * @param path   当前节点的路径——树的情况下
     * @param parent 当前节点的父节点——树的情况下，如果parent为null，那么说明当前节点是树的头节点
     */
    public Node(T data, String tag, String path, int index, Node parent) {
        this.data = data;
        this.tag = tag;
        this.path = path;
        this.index = index;
        this.parent = parent;
        next = null;
        if (path != null && path.length() > 0) {
            pathSplite = path.split("/");
        }
    }

    public String[] getPathSplite() {
        return pathSplite;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Node<T>> getNext() {
        return next;
    }

    public void setNext(List<Node<T>> next) {
        this.next = next;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "tag='" + tag + '\'' +
                ", path='" + path + '\'' +
                ", childSize= " + (next == null ? -1 : next.size()) +
                ", index= " + index +
                ", parent= " + (parent == null ? "First" : parent.getTag()) +
                '}';
    }
}
