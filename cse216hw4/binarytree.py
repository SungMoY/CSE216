class BinaryTree:
    def __init__(self, data=None):
        self.data = data
        self.left = None
        self.right = None

    @property
    def data(self):
        return self.__data

    @data.setter
    def data(self, data):
        self.__data = data

    def __iter__(self):
        if self.data is not None:
            yield self.data
        if self.left is not None:
            for child in self.left.__iter__():
                yield child
        if self.right is not None:
            for child in self.right.__iter__():
                yield child

    def add_leftchild(self, tree):
        if type(self.data) != type(tree.data):
            raise TypeError("Type mismatch between "+type(self.data).__name__+" and "+type(tree.data).__name__)
        else:
            self.left = tree

    def add_rightchild(self, tree):
        if type(self.data) != type(tree.data):
            raise TypeError("Type mismatch between "+type(self.data).__name__+" and "+type(tree.data).__name__)
        else:
            self.right = tree