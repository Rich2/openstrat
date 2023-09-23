/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

class CharExtensions(thisChar: Char)
{
  def isHexaDigit: Boolean = thisChar match
  { case d if d.isDigit => true
    case al if al <= 'F' && al >= 'A' => true
    case al if al <= 'f' && al >= 'a' => true
    case _ => false
  }

  /** Returns a [[String]] that repeats this [[Char]] the given parameter number of times. */
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
{ /** An alternative fold extension method for [[Option]] with a single parameter list. */
  def fld[B](noneValue: => B, fSome: A => B): B = thisOption match
  { case None => noneValue
    case Some(a) => fSome(a)
  }

  /** safe get. Seeks an implicit value for the [[GefaultValue]] type class for the type of the option. Returns the value if a some else returns the
   * default value. */
  def getSafe(implicit ev: DefaultValue[A]): A = thisOption match
  { case Some(a) => a
    case None => ev.default
  }

  /** An alternative fold extension method for [[Option]] where it searches for an implicit instance of [[DefaultValue]][B] and uses the default value
   *  in the case of None' */
  def defaultFold[B](fSome: A => B)(implicit ev: DefaultValue[B]): B = thisOption match
  { case None => ev.default
    case Some(a) => fSome(a)
  }

  def toArr[AA <: Arr[A]](implicit build: ArrMapBuilder[A, AA]): AA = thisOption.fold(build.uninitialised(0)){ a =>
    val res = build.uninitialised(1)
    build.indexSet(res, 0, a)
    res
  }

  def map2[B, C](ob: Option[B])(f: (A, B) => C): Option[C] = thisOption.fold[Option[C]](None)(a => ob.fold[Option[C]](None)(b => Some(f(a, b))))

  def toEMon(errs: StrArr): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => Bad(errs)
  }

  /** Convert to EMon with a single error if [[None]] */
  def toEMon1(fp: TextSpan, detail: String): EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => bad1(fp, detail)
  }

  def toEMon: EMon[A] = thisOption match
  { case Some(a) => Good(a)
    case None => Bad(StrArr())
  }

  /** Keeps the same value if [[Some]], lazily takes the parameter value if [[None]]. */
  def replaceNone(newOption: => Option[A]) = thisOption match
  { case None => newOption
    case _ => this
  }
}