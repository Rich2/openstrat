package ostrat

trait ArrBuffHomo[A] extends Any
{ type ArrT <: ArrProdHomo[A]
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  def toArr(implicit build: ArrBuild[A, ArrT]): ArrT = ???
}

