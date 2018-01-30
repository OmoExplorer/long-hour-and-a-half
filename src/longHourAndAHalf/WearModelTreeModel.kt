package longHourAndAHalf

import javax.swing.event.TreeModelListener
import javax.swing.tree.TreeModel
import javax.swing.tree.TreePath

class WearModelTreeModel(val models: List<Any>) : TreeModel {   // TODO
    /**
     * Returns `true` if `node` is a leaf.
     * It is possible for this method to return `false`
     * even if `node` has no children.
     * A directory in a filesystem, for example,
     * may contain no files; the node representing
     * the directory is not a leaf, but it also has no children.
     *
     * @param node a node in the tree, obtained from this data source
     * @return true if `node` is a leaf
     */
    override fun isLeaf(node: Any?) = node is WearModel

    /**
     * Returns the number of children of `parent`.
     * Returns 0 if the node
     * is a leaf or if it has no children. `parent` must be a node
     * previously obtained from this data source.
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the number of children of the node `parent`
     */
    override fun getChildCount(parent: Any?) = (parent as WearCategoryNode).contents.size

    /**
     * Removes a listener previously added with
     * `addTreeModelListener`.
     *
     * @see .addTreeModelListener
     *
     * @param l the listener to remove
     */
    override fun removeTreeModelListener(l: TreeModelListener?) {
    }

    /**
     * Messaged when the user has altered the value for the item identified
     * by `path` to `newValue`.
     * If `newValue` signifies a truly new value
     * the model should post a `treeNodesChanged` event.
     *
     * @param path path to the node that the user has altered
     * @param newValue the new value from the TreeCellEditor
     */
    override fun valueForPathChanged(path: TreePath?, newValue: Any?) {
    }

    /**
     * Returns the index of child in parent. If either `parent`
     * or `child` is `null`, returns -1.
     * If either `parent` or `child` don't
     * belong to this tree model, returns -1.
     *
     * @param parent a node in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1 if either
     * `child` or `parent` are `null`
     * or don't belong to this tree model
     */
    override fun getIndexOfChild(parent: Any?, child: Any?): Int = (parent as WearCategoryNode).contents.indexOf(child)

    /**
     * Returns the child of `parent` at index `index`
     * in the parent's
     * child array.  `parent` must be a node previously obtained
     * from this data source. This should not return `null`
     * if `index`
     * is a valid index for `parent` (that is `index >= 0 &&
     * index < getChildCount(parent`)).
     *
     * @param parent a node in the tree, obtained from this data source
     * @param index index of child to be returned
     * @return the child of `parent` at index `index`
     */
    override fun getChild(parent: Any?, index: Int) =
            (parent as WearCategoryNode).contents[index]

    /**
     * Adds a listener for the `TreeModelEvent`
     * posted after the tree changes.
     *
     * @param l the listener to add
     * @see .removeTreeModelListener
     */
    override fun addTreeModelListener(l: TreeModelListener?) {
    }

    /**
     * Returns the root of the tree.  Returns `null`
     * only if the tree has no nodes.
     *
     * @return the root of the tree
     */
    override fun getRoot() = models[0]
}