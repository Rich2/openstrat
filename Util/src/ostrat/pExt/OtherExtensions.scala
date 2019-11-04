/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pExt
import reflect.ClassTag

class CharExtensions(thisChar: Char)
{
  def isHexaDigit: Boolean = thisChar match
  { case d if d.isDigit => true
    case al if al <= 'F' && al >= 'A' => true
    case al if al <= 'f' && al >= 'a' => true
    case _ => false
  }

  def timesString(n: Int): String =
  {
    var acc = ""
    n.doTimes(() => acc += thisChar.toString)
    acc
  }
}

class LongExtensions(val thisLong: Long) extends AnyVal
{ def million: Long = thisLong * 1000000L
  def billion: Long = thisLong * 1000000000L
}

class OptionExtensions[A](thisOption: Option[A])
{
  def fld[B](noneValue: => B, fSome: A => B): B = thisOption match
  { case None => noneValue
    case Some(a) => fSome(a)
  }
  def toArr(implicit ct: ClassTag[A]): Arr[A] = thisOption.fold(Arr())(Arr[A](_))

  def map2[B, C](ob: Option[B], f: (A, B) => C): Option[C] = thisOption.fold[Option[C]](None)(a => ob.fold[Option[C]](None)(b => Some(f(a, b))))

  def toEMon(errs: StrList): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => Bad(errs)
  }

  def toEMon1(fp: TextSpan, detail: String): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => bad1(fp, detail)
  }
}