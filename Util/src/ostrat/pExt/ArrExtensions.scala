package ostrat
package pExt

class ArrExtensions[A](thisArr: ArrOld[A])
{
  /** Concatenates Arr if Some. Returns original Arr if operand is None. */
 // @deprecated def optionConcat[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
 @deprecated def appendsOption(optElems: Option[ArrOld[A]]): ArrOld[A] = optElems.fold[ArrOld[A]](thisArr)(bs => thisArr ++ bs)
}