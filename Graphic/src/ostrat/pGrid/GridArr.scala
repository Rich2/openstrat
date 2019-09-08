package ostrat
package pGrid

trait GridArr[T] extends Any
{ def array: Array[T]
}

trait SqSqArr[T] extends Any with GridArr[T]
{ def size: Int
}
