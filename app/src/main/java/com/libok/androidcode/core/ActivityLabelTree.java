package com.libok.androidcode.core;

import android.support.annotation.NonNull;

import com.libok.androidcode.bean.AppLabelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liboK  2018/08/07 下午 06:12
 */
public class ActivityLabelTree<T> {

    private static final String RETURN_HAVE = "have";

    private AppLabelBean<T> first;

    public ActivityLabelTree(T data, String tag, String path) {
        first = new AppLabelBean<>(data, tag, path, 0, null);
    }

    /**
     * 获取头节点
     *
     * @return 返回头节点
     */
    public AppLabelBean<T> getFirst() {
        return first;
    }

    /**
     * 搜寻要插入节点的父节点
     * @param node      轮着查询的node，从头节点开始
     * @param lastTag   要插入节点的上一级的Tag
     * @param tag       要插入节点的Tag
     * @param index     要插入节点的高度
     * @return          返回查找到的父节点
     */
    private AppLabelBean<T> searchParent(AppLabelBean<T> node, String lastTag, String tag, int index) {

        if (index > 1 && index - node.getIndex() == 1) {
            if (!node.getTag().equals(lastTag)) {
                return null;
            }
        }

        List<AppLabelBean<T>> next = node.getNext();
        if (next == null) {
            next = new ArrayList<>();
            node.setNext(next);
        }

        if (index - node.getIndex() == 1) {
            boolean haveSameNode = false;
            for (AppLabelBean<T> tNode : next) {
                if (tNode.getTag().equals(tag)) {
                    haveSameNode = true;
                    break;
                }
            }
            if (haveSameNode) {
                return new AppLabelBean<>(RETURN_HAVE);
            } else {
                return node;
            }
        }
        for (AppLabelBean<T> parentNode : next) {
            AppLabelBean<T> tNode = searchParent(parentNode, lastTag, tag, index);
            if (tNode != null) {
                if (tNode.getTag().equals(RETURN_HAVE)) {
                    break;
                } else {
                    return tNode;
                }
            }/*else{
                continue;
            }*/
        }

        return null;
    }

    /**
     * 插入节点
     *
     * @param labelBean 要插入的节点
     * @return 返回是否插入成功
     */
    public boolean add(@NonNull AppLabelBean<T> labelBean) {
        String tag = labelBean.getTag();
        int index = labelBean.getIndex();
        String lastTag;
        if (index == 1) {
            lastTag = "first";
        } else {
            lastTag = labelBean.getPathSplite()[index - 2];
        }
        AppLabelBean<T> tNode = searchParent(first, lastTag, tag, index);
        if (tNode == null) {
//            System.out.println("tNode is null");
            return false;
        }
        List<AppLabelBean<T>> next = tNode.getNext();
        if (next == null) {
//            System.out.println("tNode getNext is null");
            return false;
        } else {
            labelBean.setParent(tNode);
            next.add(labelBean);
        }

        return true;
    }

//    /**
//     * 删除节点
//     *
//     * @param node 要删除的节点
//     * @return 是否删除成功
//     */
//    public boolean remove(Node node) {
//        return false;
//    }

    private void getChildNode(StringBuilder stringBuilder, AppLabelBean<T> node) {
        for (int i = 0; i < node.getIndex(); i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(node.toString());
        stringBuilder.append("\n");
        List<AppLabelBean<T>> child = node.getNext();
        if (child != null) {
            for (AppLabelBean<T> node1 : child) {
                getChildNode(stringBuilder, node1);
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(first.toString());
        for (AppLabelBean<T> node : first.getNext()) {
            stringBuilder.append("\n");
            getChildNode(stringBuilder, node);
        }

        return stringBuilder.toString();
    }
}
