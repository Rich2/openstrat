/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an errors handler. So it makes sense to use a dedicated class. */
sealed trait EMon[+A] extends EMonBase[A]
{
  @inline def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB

  def map[B](f: A => B): EMon[B]

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects not a value. */
  def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit

  def flatMap[B](f: A => EMon[B]): EMon[B]
  def flatMap2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2]

  /** Gets the value of Good, throws exception on Bad. */
  def get: A

  /** Gets the value of Good or returns the elseValue parameter if Bad. */
  def getElse[A1 >: A](elseValue: => A1): A1

  /** Use this EMon if good else use other EMon passed as parameter.*/
  //def elseTry[A1 >: A](other: EMon[A1]): EMon[A1]

  def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2]
  def toEither: Either[Strings, A]
  def mapToEither[D](f: A => D): Either[Strings, D]
  def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D]
  def isGood: Boolean
  def isBad: Boolean
}

object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Persist[A]): EMon[A] = thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findType[A]))
    def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Persist](f: A => Unit): Unit = findType[A].forGood(f)
    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] =
      thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findSett[A](settingStr)))
    def findSettingElse[A: Persist](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)
    def findSomeSetting[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(implicit ev: Persist[A]): EMon[A]
    def findSomeSettingElse[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }

  implicit def showImplicit[A](implicit ev: Show[A]): Show[EMon[A]] =
    ShowSum2("EMon", Good.GoodShowImplicit(ev),
      Bad.BadShowImplicit(ev))
}

trait Opt[+A] extends EMon[A] with OptBase[A]

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based
 *  Right[List[String], +A]. */
case class Good[+A](val value: A) extends Opt[A] with GoodBase[A]
{
  override def map[B](f: A => B): EMon[B] = Good[B](f(value))
  override def mMap[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B](f: A => EMon[B]): EMon[B] = f(value)
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fGood(value)

  override def fold[B](noneValue: => B)(fGood: A => B): B = fGood(value)

  @inline override def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB =
  { val res = build.imutNew(1)
    build.imutSet(res, 0, f(value))
    res
  }

 // @inline override def fld[B](fGood: A => B, fBad: Strings => B) : B = fGood(value)
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def flatMap2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = f(value)
  override def forGood(f: A => Unit): Unit = f(value)
  override def get: A = value
  override def getElse[A1 >: A](elseValue: => A1): A1 = value
  //override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A] = this
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
  override def toEither: Either[Strings, A] = Right(value)
  override def mapToEither[D](f: A => D): Either[Strings, D] = Right(f(value))
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = f(value)
  override def isGood: Boolean = true
  override def isBad: Boolean = false
}

object Good
{
  implicit def GoodShowImplicit[A](implicit ev: Show[A]): Show[Good[A]] = new Show[Good[A]] with ShowCompound[Good[A]]
  { override def syntaxDepth: Int = ev.syntaxDepth + 1
    override def typeStr: String = "Good" + ev.typeStr.enSquare
    override def showSemi(obj: Good[A]): String = ev.showSemi(obj.value)
    override def showComma(obj: Good[A]): String = ev.showComma(obj.value)
  }
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
case class Bad[+A](errs: Strings) extends EMon[A] with BadBase[A]
{ override def map[B](f: A => B): EMon[B] = Bad[B](errs)
  override def mMap[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B](f: A => EMon[B]): EMon[B] = Bad[B](errs)
  @inline override def fold[B](noneValue: => B)(fGood: A => B): B = noneValue
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fBad(errs)
  @inline override def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB = build.imutNew(0)

  //@inline override def fld[B](fGood: A => B, fBad: Strings => B) : B = fBad(errs)
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fBad(errs)
  override def flatMap2[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = new Bad2[B1, B2](errs)

  override def getElse[A1 >: A](elseValue: => A1): A1 = elseValue
 // override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1] = otherValue
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def toEither: Either[Strings, A] = Left(errs)
  override def mapToEither[D](f: A => D): Either[Strings, D] = Left(errs)
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = (Left(errs))
  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def get: A = excep("Called get on Bad.")
}

object Bad
{
  implicit def BadShowImplicit[A](implicit ev: Show[A]): Show[Bad[A]] = new Show[Bad[A]] with ShowCompound[Bad[A]]
  {
    override def syntaxDepth: Int = 2
    override def typeStr: String = "Bad" + ev.typeStr.enSquare
    override def showSemi(obj: Bad[A]): String = obj.errs.mkString("; ")
    override def showComma(obj: Bad[A]): String = ??? // obj.errs.semiFold
  }
}

object NoGood extends Bad[Nothing](Refs()) with Opt[Nothing]

