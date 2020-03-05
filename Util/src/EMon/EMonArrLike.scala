package ostrat
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

trait EMonArrLike[+A, AA <: ArrImut[A]] extends EMonBase[AA]
{

}

trait GoodArrLike[+A, AA <: ArrImut[A]] extends EMonArrLike[A, AA] with GoodBase[AA]
