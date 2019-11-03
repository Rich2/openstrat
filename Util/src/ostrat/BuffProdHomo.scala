package ostrat

trait ArrBuffHomo[A] extends Any
{ type ArrT <: ArrProdHomo[A]
  def append(newElem: A): Unit
  def addAll(newElems: ArrT): Unit
  def toArr(implicit build: ArrBuild[A, ArrT]): ArrT = ???
}

