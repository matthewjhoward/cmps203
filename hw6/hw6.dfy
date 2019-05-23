/*
-- Program: hw6.dfy
-- Authors: Matthew J. Howard and Alex Williamson
-- Date: 5/26/2019
-- Details: Fills in four functions and a lemma needed to prove the ensures property for the lemma in the Dafny language 
--
--          On this homework, we worked in total for 4 hours.
--          Matt worked independently on filling in the append() and listContains() functions for 1 hour.
--          Alex worked independently on filling in the flatten() and treeContains() functions for 1 hour.
--          We worked together on merging code/ideas to fill in the lemma sameElements for 2 hours. 
*/  

datatype Tree<T> = Leaf | Node(Tree<T>, Tree<T>, T)
datatype List<T> = Nil | Cons(T, List<T>)

function flatten<T>(tree:Tree<T>):List<T> 
{
    match tree
    case Leaf => Nil
    case Node(leftChild, rightChild, elem) => Cons(elem, append(flatten(leftChild), flatten(rightChild)))
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

ghost method elementDistributesOverAppend<T>(e: T, xs: List<T>, ys: List<T>)
ensures listContains(xs, e) || listContains(ys, e) <==> listContains(append(xs, ys), e)
{
    match (xs) {
        case Nil => {}
        case Cons(x, xs') => {
            elementDistributesOverAppend(e, xs', ys);

            assert listContains(append(xs, ys), e)
                == listContains(append(Cons(x, xs'), ys), e)
                == listContains(Cons(x, append(xs', ys)), e)
                == (x == e || listContains(append(xs', ys), e))
                == (x == e || listContains(xs', e) || listContains(ys, e))
                == listContains(xs, e) || listContains(ys, e);
        }
    }
}

lemma sameElements<T>(tree:Tree<T>, element:T)
ensures treeContains(tree, element) <==> listContains(flatten(tree), element) 
{	
    match tree {
        case Leaf => {}
        case Node(leftChild, rightChild, elem) => {
            // sameElements(leftChild, element);
            // sameElements(rightChild, element);

            assert listContains(flatten(tree), element)
                == listContains(flatten(Node(leftChild, rightChild, elem)), element)
                == listContains(Cons(elem, append(flatten(leftChild), flatten(rightChild))), element)
                == (listContains(append(flatten(leftChild), flatten(rightChild)), element) || elem == element);

            elementDistributesOverAppend(element, flatten(leftChild), flatten(rightChild));

            assert (listContains(append(flatten(leftChild), flatten(rightChild)), element) || elem == element)   
                == (listContains(flatten(leftChild), element) || listContains(flatten(rightChild), element)  || elem == element)
                == (treeContains(leftChild, element) || treeContains(rightChild, element)  || elem == element) 
                == treeContains(tree, element); 
        }
            
    }
}