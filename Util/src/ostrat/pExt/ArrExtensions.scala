package ostrat
package pExt

class ArrExtensions[A](thisArr: Arr[A])
{
  /** Concatenates Arr if Some. Returns original Arr if operand is None. */
  @deprecated def optConcat[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
}