/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an errors handler. So it makes sense to use a dedicated class. */
sealed trait EMon[+A]
{ /** Maps the Good case of this EMon with the function. */
  def map[B](f: A => B): EMon[B]

  def flatMap[B](f: A => EMon[B]): EMon[B]
  def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2]

  /** 2 type parameters, maps the Good case of this [[EMon]], with the [[Good]] case of an additional [[EMon]] of a different type. */
  def map2[A2, R](e2: EMon[A2])(f: (A, A2) => R): EMon[R]

  /** 3 type parameters, maps the Good case of this [[EMon]], with the [[Good]] cases of an additional 3 [[EMon]]s of a different types. */
  def map3[A2, A3, R](e2: EMon[A2], e3: EMon[A3])(f: (A, A2, A3) => R): EMon[R]

  /** 4 type parameters, maps the Good case of this [[EMon]], with the [[Good]] cases of an additional 3 [[EMon]]s of a different types. */
  def map4[A2, A3, A4, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4])(f: (A, A2, A3, A4) => R): EMon[R]

  /** 5 type parameters, maps the Good case of this [[EMon]], with the [[Good]] cases of an additional 4 [[EMon]]s of a different types. */
  def map5[A2, A3, A4, A5, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5])(f: (A, A2, A3, A4, A5) => R): EMon[R]

  /** 6 type parameters, maps the Good case of this [[EMon]], with the [[Good]] cases of an additional 5 [[EMon]]s of a different types. */
  def map6[A2, A3, A4, A5, A6, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5], e6: EMon[A6])(f: (A, A2, A3, A4, A5, A6) => R): EMon[R]

  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid
   * unnecessary boxing of primitive values. */
  def getElse(elseValue: A @uncheckedVariance): A

  def errs: StrArr

  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit

  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a
   *  value. This method is implemented in the leaf Good classes to avoid boxing. */
  def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit

  /** Gets the value of Good, throws exception on Bad. */
  def get: A

  def fold[B](noneValue: => B)(fGood: A => B): B
  def fld[B](noneValue: => B, fGood: A => B): B

  def toOption: Option[A]
  def toEither: Either[StrArr, A]
  def isGood: Boolean
  def isBad: Boolean

  /** Maps Good to Right[Strings, D] and Bad to Left[Strings, D]. These are implemented in the base traits GoodBase[+A] and BadBase[+A] as
   *  Either[+A, +B] boxes all value classes. */
  def mapToEither[D](f: A => D): Either[StrArr, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2]

  def mapToOption[B](f: A => B): Option[B]
  def flatMap2ToOption[A2, B](o2: EMon[A2], f: (A, A2) => B): Option[B]

  def goodOrOther[A1 >: A](otherEMon: => EMon[A1] @uncheckedVariance): EMon[A1]
}

/** Companion object for EMon triat contains implict class for EMon returning extension methods on [[String]] and Show implicit instance. */
object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Unshow[A]): EMon[A] = thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findType[A]))
    def findTypeElse[A: Unshow](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Unshow](f: A => Unit): Unit = findType[A].forGood(f)
    def findSetting[A](settingStr: String)(implicit ev: Unshow[A]): EMon[A] =
      thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findSetting[A](settingStr)))
    def findSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)
    def findSomeSetting[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(implicit ev: Persist[A]): EMon[A]
    def findSomeSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }

  implicit def showImplicit[A](implicit ev: Show[A]): Show[EMon[A]] =
    ShowSum2("EMon", Good.GoodShowImplicit(ev),
      Bad.BadShowImplicit(ev))

  implicit class EMonSeqGen[A, S <: Sequ[A]](thisES: EMon[S])
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
  @inline override def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B = fGood(value)

  override def fold[B](noneValue: => B)(fGood: A => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: A => B): B = fGood(value)

  override def map2[A2, R](e2: EMon[A2])(f: (A, A2) => R): EMon[R] = e2.map{ a2 => f(value, a2) }
  override def map3[A2, A3, R](e2: EMon[A2], e3: EMon[A3])(f: (A, A2, A3) => R): EMon[R] = e2.map2(e3)({ (a2, a3) => f(value, a2, a3) })

  override def map4[A2, A3, A4, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4])(f: (A, A2, A3, A4) => R): EMon[R] =
    e2.map3(e3, e4){(a2, a3, a4) => f(value, a2, a3, a4) }

  override def map5[A2, A3, A4, A5, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5])(f: (A, A2, A3, A4, A5) => R): EMon[R] =
    e2.map4(e3, e4, e5){ (a2, a3, a4, a5) => f(value, a2, a3, a4, a5) }

  override def map6[A2, A3, A4, A5, A6, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5], e6: EMon[A6])(f: (A, A2, A3, A4, A5, A6) => R): EMon[R] =
    e2.map5(e3, e4, e5, e6){ (a2, a3, a4, a5, a6) => f(value, a2, a3, a4, a5, a6) }

  override def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit = fGood(value)
  override def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = f(value)
  override def forGood(f: A => Unit): Unit = f(value)
  override def get: A = value
  override def getElse(elseValue: A @uncheckedVariance): A = value
  //def value: A
  override def errs: StrArr = StrArr()
  override def toOption: Option[A] = Some(value)
  override def toEither: Either[StrArr, A] = Right(value)
  override def isGood: Boolean = true
  override def isBad: Boolean = false
  override def mapToEither[D](f: A => D): Either[StrArr, D] = Right(f(value))
  override def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D] = f(value)
  override def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
  override def mapToOption[B](f: A => B): Option[B] = Some[B](f(value))
  override def flatMap2ToOption[A2, B](e2: EMon[A2], f: (A, A2) => B): Option[B] = e2.fld(None, a2 => Some(f(value, a2)))

  /** Returns this [[EMon]][A] if this is Good, else returns the operand EMon[A]. */
  override def goodOrOther[A1 >: A](otherEMon: => EMon[A1] @uncheckedVariance): Good[A] = this
}

object Good
{
  implicit def GoodShowImplicit[A](implicit ev: Show[A]): Show[Good[A]] = new Show[Good[A]] with ShowCompound[Good[A]]
  { override def syntaxDepthT(obj: Good[A]): Int = ev.syntaxDepthT(obj.value) + 1
    override def typeStr: String = "Good" + ev.typeStr.enSquare

    override def showDecT(obj: Good[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
class Bad[+A](val errs: StrArr) extends EMon[A]
{ override def toString: String =  "Bad" + errs.foldLeft("")(_ + _.enquote).enParenth
  override def map[B](f: A => B): EMon[B] = Bad[B](errs)
  override def flatMap[B](f: A => EMon[B]): EMon[B] = Bad[B](errs)
  override def fold[B](noneValue: => B)(fGood: A => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: A => B): B = noneValue
  @inline override def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B = fBad(errs)

  override def map2[A2, R](e2: EMon[A2])(f: (A, A2) => R): EMon[R] = Bad[R](errs ++ e2.errs)
  override def map3[A2, A3, R](e2: EMon[A2], e3: EMon[A3])(f: (A, A2, A3) => R): EMon[R] = Bad[R](errs ++ e2.errs ++ e3.errs)
  override def map4[A2, A3, A4, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4])(f: (A, A2, A3, A4) => R): EMon[R] =
    Bad[R](errs ++ e2.errs ++ e3.errs ++ e4.errs)

  override def map5[A2, A3, A4, A5, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5])(f: (A, A2, A3, A4, A5) => R): EMon[R] =
    Bad[R](errs ++ e2.errs ++ e3.errs ++ e4.errs ++ e5.errs)

  override def map6[A2, A3, A4, A5, A6, R](e2: EMon[A2], e3: EMon[A3], e4: EMon[A4], e5: EMon[A5], e6: EMon[A6])(f: (A, A2, A3, A4, A5, A6) => R):
    EMon[R] = Bad[R](errs ++ e2.errs ++ e3.errs ++ e4.errs ++ e5.errs)

  override def toEMon2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = new Bad2[B1, B2](errs)

  override def getElse(elseValue: A @uncheckedVariance): A = elseValue
  override def forGood(f: A => Unit): Unit = {}
  override def toOption: Option[A] = None
  override def toEither: Either[StrArr, A] = Left(errs)
  override def get: A = excep("Called get on Bad.")
  override def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit = fBad(errs)
  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def mapToEither[D](f: A => D): Either[StrArr, D] = Left(errs)
  override def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D] = (Left(errs))
  override def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def mapToOption[B](f: A => B): Option[B] = None
  override def flatMap2ToOption[A2, B](e2: EMon[A2], f: (A, A2) => B): Option[B] = None
  override def goodOrOther[A1 >: A](otherEMon: => EMon[A1] @uncheckedVariance): EMon[A1] = otherEMon
}

object Bad
{
  def apply[A](errs: StrArr): Bad[A] = new Bad[A](errs)
  def unapplySeq(inp: Any): Option[Seq[String]] = inp match
  { case b: Bad[_] => Some(b.errs.toList)
    case _ => None
  }

  implicit def BadShowImplicit[A](implicit ev: Show[A]): Show[Bad[A]] = new Show[Bad[A]] with ShowCompound[Bad[A]]
  { override def syntaxDepthT(obj: Bad[A]): Int = 2
    override def typeStr: String = "Bad" + ev.typeStr.enSquare
    override def showDecT(obj: Bad[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}