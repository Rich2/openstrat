package ostrat

trait ArrOff[A, ArrT <: ArrImut[A]] extends Any
{ def offset0: Int
  @inline def apply(index: Int)(implicit arr: ArrT): A
  def length(implicit arr: ArrT): Int
  @inline def offset1: Int = offset0 + 1
  @inline def offset2: Int = offset0 + 2
  @inline def offset3: Int = offset0 + 3
  @inline def offset4: Int = offset0 + 4
  def forall(p: A => Boolean)(implicit arr: ArrT): Boolean = ??? //forall(0, length - 1)(p)
  def forN(endIndex: Int, p: A => Boolean)(implicit arr: ArrT): Boolean =
  {
    if(endIndex >= length) false
    else
    {
      var count = 0
      var acc = true
      while(acc == true & count <= endIndex)
        if (p(apply(count))) count += 1
        else acc = false
      acc
    }
  }
  def forRange(startIndex: Int, endIndex: Int, p: A => Boolean)(implicit arr: ArrT): Boolean = ???
 /* def drop(n: Int): ArrOff[A] = new ArrOff(offset + n)
  def drop1: ArrOff[A] = new ArrOff(offset + 1)
  def drop2: ArrOff[A] = new ArrOff(offset + 2)
  def length(implicit array: Array[A]): Int = array.length - offset
  def span(p: A => Boolean)(implicit array: Array[A]): (ArrImut[A], ArrOff[A]) = ???
*/}

/*object ArrOff0
{   def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[Unit] = ife(array.length - inp.offset <= 0, Some(), None)
}*/

/*object ArrOff1
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, ArrOff[A])] =
    ife(array.length - inp.offset >= 1, Some((array(inp.offset), inp.drop1)), None)
}*/

/*object ArrOff2
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, A, ArrOff[A])] =
    ife(array.length - inp.offset >= 2, Some((array(inp.offset), (array(inp.offset + 1)), inp.drop2)), None)
}*/

/*object ArrOff3
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, A, A, ArrOff[A])] =
    ife(array.length - inp.offset >= 3, Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), inp.drop(3))), None)
}*/
