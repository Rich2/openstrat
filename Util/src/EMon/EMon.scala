/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an errors handler. So it makes sense to use a dedicated class. */
sealed trait EMon[+A] extends EMonBase[A]
{
  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid
   * unnecessary boxing of primitive values. */
  def getElse[A1 >: A](elseValue: => A1): A1
  @deprecated def mapOld[B](f: A => B): EMon[B]
  @deprecated def flatMapOld[B](f: A => EMon[B]): EMon[B]
  def flatMap2Old[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2]
}

object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Persist[A]): EMon[A] = thisEMon.flatMapOld(str => pParse.stringToStatements(str).flatMapOld(_.findType[A]))
    def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Persist](f: A => Unit): Unit = findType[A].forGood(f)
    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] =
      thisEMon.flatMapOld(str => pParse.stringToStatements(str).flatMapOld(_.findSett[A](settingStr)))
    def findSettingElse[A: Persist](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)
    def findSomeSetting[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(implicit ev: Persist[A]): EMon[A]
    def findSomeSettingElse[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }

  implicit def showImplicit[A](implicit ev: Show[A]): Show[EMon[A]] =
    ShowSum2("EMon", Good.GoodShowImplicit(ev),
      Bad.BadShowImplicit(ev))

  implicit class refsImplicit[A <: AnyRef](thisEMon: EMon[Refs[A]])
  {
    def toNewERefs: ERefs[A] = thisEMon match
    {
      case Good(rs) => GoodRefs(rs)
      case Bad(errs) => BadRefs(errs)
    }
  }
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based
 *  Right[Refs[String], +A]. */
final case class Good[+A](val value: A) extends EMon[A] with GoodBase[A]
{
  override def mapOld[B](f: A => B): EMon[B] = Good[B](f(value))
  override def map[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: A => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def flatMapOld[B](f: A => EMon[B]): EMon[B] = f(value)
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fGood(value)

  override def fold[B](noneValue: => B)(fGood: A => B): B = fGood(value)

 // @inline override def fld[B](fGood: A => B, fBad: Strings => B) : B = fGood(value)
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def flatMap2Old[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = f(value)
  override def forGood(f: A => Unit): Unit = f(value)
  override def get: A = value
  override def getElse[A1 >: A](elseValue: => A1): A1 = value
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
{ override def mapOld[B](f: A => B): EMon[B] = Bad[B](errs)
  override def map[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: A => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMapOld[B](f: A => EMon[B]): EMon[B] = Bad[B](errs)
  @inline override def fold[B](noneValue: => B)(fGood: A => B): B = noneValue
  @inline override def foldErrs[B](fGood: A => B)(fBad: Strings => B): B = fBad(errs)

  //@inline override def fld[B](fGood: A => B, fBad: Strings => B) : B = fBad(errs)

  override def flatMap2Old[B1, B2](f: A => EMon2[B1, B2]): EMon2[B1, B2] = new Bad2[B1, B2](errs)

  override def getElse[A1 >: A](elseValue: => A1): A1 = elseValue
 // override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1] = otherValue

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

object NoGood extends Bad[Nothing](Refs())