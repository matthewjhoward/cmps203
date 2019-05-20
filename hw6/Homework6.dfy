datatype Tree<T> = Leaf | Node(Tree<T>, Tree<T>, T)
datatype List<T> = Nil | Cons(T, List<T>)

// Takes a tree argument and returns a list
function flatten<T>(tree:Tree<T>):List<T> 
{
    match tree
    case Leaf => Nil
    case Node(leftChild, rightChild, elem) => append(flatten(leftChild), Cons(elem, flatten(rightChild)))
}

// Takes two lists and returns a list
function append<T>(xs:List<T>, ys:List<T>):List<T>
{
    match xs
    case Nil => ys
    case Cons(elem, list) => Cons(elem, append(list, ys))
}

// Takes a tree and an element in tree and returns a bool
function treeContains<T>(tree:Tree<T>, element:T):bool
{
    match tree 
    case Leaf => false
    case Node(leftChild, rightChild, elem) => treeContains(leftChild, element) || treeContains(rightChild, element) || elem == element
}

// Takes a list and an element in list and returns bool
function listContains<T>(xs:List<T>, element:T):bool
{
    match xs
    case Nil => false
    case Cons(elem, list) => listContains(list, element) || elem == element
}


lemma sameElements<T>(tree:Tree<T>, element:T)
ensures treeContains(tree, element) <==> listContains(flatten(tree), element) 
{	
    match tree {
        case Leaf =>
            // something
        case Node(leftChild, rightChild, elem) =>
            // something
    }
}