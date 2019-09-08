package ostrat
package pGrid

trait GridArr[T] extends Any
{ type GridT <: GridArr[T]
  def array: Array[T]
  def getInd(x: Int, y: Int): Int
  def thisFac: Array[T] => GridT
  //def modXY(x: Int, y: Int, value: T):
}

trait SqSqArr[T] extends Any with GridArr[T]
{ type GridT <: GridArr[T]
  def size: Int
  override def getInd(x: Int, y: Int): Int = (y - 1) / 2 * size + x / 2
}
