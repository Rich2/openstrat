package ostrat

trait ArrBuffHomo[A] extends Any with ArrayLike[A]
{ type ArrT <: ArrProdHomo[A]
  def elemSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  def toArr(implicit build: ArrBuild[A, ArrT]): ArrT = ???
}

