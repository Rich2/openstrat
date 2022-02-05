/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an errors handler. So it makes sense to use a dedicated class. */
sealed trait EMon[+A]
{ def map[B](f: A => B): EMon[B]
  def flatMap[B](f: A => EMon[B]): EMon[B]
  def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2]

  def map2[B, R](mb: EMon[B])(f: (A, B) => R): EMon[R]
  def map3[B, C, R](mb: EMon[B], mc: EMon[C])(f: (A, B, C) => R): EMon[R]
  def map4[B, C, D, R](mb: EMon[B], mc: EMon[C], md: EMon[D])(f: (A, B, C, D) => R): EMon[R]

  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid
   * unnecessary boxing of primitive values. */
  def getElse(elseValue: A @uncheckedVariance): A

  def errs: Strings

  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit

  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: Strings => B): B

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a
   *  value. This method is implemented in the leaf Good classes to avoid boxing. */
  def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit

  /** Gets the value of Good, throws exception on Bad. */
  def get: A

  def fold[B](noneValue: => B)(fGood: A => B): B
  def fld[B](noneValue: => B, fGood: A => B): B


  def toEither: Either[Strings, A]
  def isGood: Boolean
  def isBad: Boolean

  /** Maps Good to Right[Strings, D] and Bad to Left[Strings, D]. These are implemented in the base traits GoodBase[+A] and BadBase[+A] as
   *  Either[+A, +B] boxes all value classes. */
  def mapToEither[D](f: A => D): Either[Strings, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2]

  def mapToOption[B](f: A => B): Option[B]
  def flatMap2ToOption[A2, B](o2: EMon[A2], f: (A, A2) => B): Option[B]

  def goodOrOther(otherEMon: => EMon[A] @uncheckedVariance): EMon[A]
}

/** Companion object for EMon triat contains implict class for EMon returning extension methods on [[String]] and Show implicit instance. */
object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Persist[A]): EMon[A] = thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findUniqueT[A]))
    def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Persist](f: A => Unit): Unit = findType[A].forGood(f)
    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] =
      thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findSetting[A](settingStr)))
    def findSettingElse[A: Persist](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)
    def findSomeSetting[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(implicit ev: Persist[A]): EMon[A]
    def findSomeSettingElse[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }

  implicit def showImplicit[A](implicit ev: ShowT[A]): ShowT[EMon[A]] =
    ShowSum2("EMon", Good.GoodShowImplicit(ev),
      Bad.BadShowImplicit(ev))

  implicit class EMonSeqGen[A, S <: SeqGen[A]](thisES: EMon[S])
  {
    /** Method on EMon[SeqGen[A]]. If this is good, the sequence is mapped with a function from A to EMon[B]. If that mapping produces on Good value,
     * the unique Good value is returned.*/
    def seqMapUniqueGood[B](f: A => EMon[B]): EMon[B] = thisES.flatMap{ thisSeq =>
      var count = 0
      var acc: EMon[B] = badNone[B]("No elem of type found")
      thisSeq.foreach { a =>
        val eb: EMon[B] = f(a)
        if (eb.isGood) { count += 1; acc = eb }
      }
      ife (count < 2, acc, badNone (s"$count values found") )
    }
  }
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based
 *  Right[Refs[String], +A]. */
final case class Good[+A](val value: A) extends EMon[A]
{
  override def map[B](f: A => B): EMon[B] = Good[B](f(value))
  override def flatMap[B](f: A => EMon[B]): EMon[B] = f(value)
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fGood(value)

  override def fold[B](noneValue: => B)(fGood: A => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: A => B): B = fGood(value)

  override def map2[B, R](mb: EMon[B])(f: (A, B) => R): EMon[R] = mb.map(b => f(value, b))
  override def map3[B, C, R](mb: EMon[B], mc: EMon[C])(f: (A, B, C) => R): EMon[R] = mb.map2(mc){(b, c) => f(value, b, c) }
  override def map4[B, C, D, R](mb: EMon[B], mc: EMon[C], md: EMon[D])(f: (A, B, C, D) => R): EMon[R] = mb.map3(mc, md){(b, c, d) => f(value, b, c, d) }
 // @inline override def fld[B](fGood: A => B, fBad: Strings => B) : B = fGood(value)
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = f(value)
  override def forGood(f: A => Unit): Unit = f(value)
  override def get: A = value
  override def getElse(elseValue: A @uncheckedVariance): A = value
  //def value: A
  override def errs: Strings = Strings()
  override def toEither: Either[Strings, A] = Right(value)
  override def isGood: Boolean = true
  override def isBad: Boolean = false
  override def mapToEither[D](f: A => D): Either[Strings, D] = Right(f(value))
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = f(value)
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
  override def mapToOption[B](f: A => B): Option[B] = Some[B](f(value))
  override def flatMap2ToOption[A2, B](e2: EMon[A2], f: (A, A2) => B): Option[B] = e2.fld(None, a2 => Some(f(value, a2)))

  /** Returns this [[EMon]][A] if this is Good, else returns the operand EMon[A]. */
  override def goodOrOther(otherEMon: => EMon[A] @uncheckedVariance): Good[A] = this
}

object Good
{
  implicit def GoodShowImplicit[A](implicit ev: ShowT[A]): ShowT[Good[A]] = new ShowT[Good[A]] with ShowCompoundT[Good[A]]
  { override def syntaxDepthT(obj: Good[A]): Int = ev.syntaxDepthT(obj.value) + 1
    override def typeStr: String = "Good" + ev.typeStr.enSquare
    //override def showSemi(obj: Good[A]): String = ev.showSemi(obj.value)
    //override def showComma(obj: Good[A]): String = ev.showComma(obj.value)

    override def showT(obj: Good[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
class Bad[+A](val errs: Strings) extends EMon[A]
{ override def toString: String =  "Bad" + errs.foldLeft("")(_ + _.enquote).enParenth
  override def map[B](f: A => B): EMon[B] = Bad[B](errs)
  override def flatMap[B](f: A => EMon[B]): EMon[B] = Bad[B](errs)
  override def fold[B](noneValue: => B)(fGood: A => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: A => B): B = noneValue
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fBad(errs)

  override def map2[B, R](mb: EMon[B])(f: (A, B) => R): EMon[R] = Bad[R](errs ++ mb.errs)
  override def map3[B, C, R](mb: EMon[B], mc: EMon[C])(f: (A, B, C) => R): EMon[R] = Bad[R](errs ++ mb.errs ++ mc.errs)
  override def map4[B, C, D, R](mb: EMon[B], mc: EMon[C], md: EMon[D])(f: (A, B, C, D) => R): EMon[R] =
    Bad[R](errs ++ mb.errs ++ mc.errs ++ md.errs)

  override def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = new Bad2[B1, B2](errs)

  override def getElse(elseValue: A @uncheckedVariance): A = elseValue
 // override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1] = otherValue
 override def forGood(f: A => Unit): Unit = {}
  override def toEither: Either[Strings, A] = Left(errs)
  override def get: A = excep("Called get on Bad.")
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fBad(errs)
  // override def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB = build.imutNew(0)

  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def mapToEither[D](f: A => D): Either[Strings, D] = Left(errs)
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = (Left(errs))
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def mapToOption[B](f: A => B): Option[B] = None
  override def flatMap2ToOption[A2, B](e2: EMon[A2], f: (A, A2) => B): Option[B] = None
  override def goodOrOther(otherEMon: => EMon[A] @uncheckedVariance): EMon[A] = otherEMon
}

object Bad
{
  def apply[A](errs: Strings): Bad[A] = new Bad[A](errs)
  def unapplySeq(inp: Any): Option[Seq[String]] = inp match
  { case b: Bad[_] => Some(b.errs.toList)
    case _ => None
  }

  implicit def BadShowImplicit[A](implicit ev: ShowT[A]): ShowT[Bad[A]] = new ShowT[Bad[A]] with ShowCompoundT[Bad[A]]
  { override def syntaxDepthT(obj: Bad[A]): Int = 2
    override def typeStr: String = "Bad" + ev.typeStr.enSquare
    override def showT(obj: Bad[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}