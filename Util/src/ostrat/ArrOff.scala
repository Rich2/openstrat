package ostrat

class ArrOff[A](val offset: Int) extends AnyVal
{ def drop(n: Int): ArrOff[A] = new ArrOff(offset + n)
  def drop1: ArrOff[A] = new ArrOff(offset + 1)
  def drop2: ArrOff[A] = new ArrOff(offset + 2)
  def length(implicit array: Array[A]): Int = array.length - offset
}

object ArrOff0
{ def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[Unit] = ife(array.length - inp.offset <= 0, Some(), None)
}

object ArrOff1
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, ArrOff[A])] =
    ife(array.length - inp.offset >= 1, Some((array(inp.offset), inp.drop1)), None)
}

object ArrOff2
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, A, ArrOff[A])] =
    ife(array.length - inp.offset >= 2, Some((array(inp.offset), (array(inp.offset + 1)), inp.drop2)), None)
}

object ArrOff3
{
  def unapply[A](inp: ArrOff[A])(implicit array: Array[A]): Option[(A, A, A, ArrOff[A])] =
    ife(array.length - inp.offset >= 3, Some((array(inp.offset), array(inp.offset + 1), array(inp.offset + 2), inp.drop(3))), None)
}
