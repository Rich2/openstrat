/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

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

  /** Returns a String. Replacing reserved HTML characters with their corresponding entities, in order to display XML code as text. Eg '>' is
   *  replaced by "&gt;". */
  def htmlReservedSubstituion: String = thisChar match
  { case '&' => "&amp;"
    case '<' => "&lt;"
    case '>' => "&gt;"
    case '\"' => "&quot;"
    case '\'' => "&qpos;"
    case '\u00A2' => "&cent;"
    case '£' => "&pound;"
    case '€' => "&euro;"
    case '@' => "&copy;"
    case '\u00Ae' => "&reg"
    case c => c.toString  
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

  def toArr[AA <: SeqImut[A]](implicit build: ArrBuilder[A, AA]): AA = thisOption.fold(build.newArr(0)){ a =>
    val res = build.newArr(1)
    build.arrSet(res, 0, a)
    res
  }

  def map2[B, C](ob: Option[B], f: (A, B) => C): Option[C] = thisOption.fold[Option[C]](None)(a => ob.fold[Option[C]](None)(b => Some(f(a, b))))

  def toEMon(errs: Strings): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => Bad(errs)
  }

  def toEMon1(fp: TextSpan, detail: String): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => bad1(fp, detail)
  }
}