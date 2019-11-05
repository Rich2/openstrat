/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an errors handler. So it makes sense to use a dedicated class. */
sealed trait EMon[+A]
{ /** Will perform action if Good. Does nothing if Bad. */
  def foreach(f: A => Unit): Unit

  /** Fold the EMon of type A into a type of B. */
  @inline def fold[B](fBad: StrList => B, fGood: A => B): B = this match
  { case Good(a) => fGood(a)
    case Bad(errs) => fBad(errs)
  }
  
  def errs: StrList
  def map[B](f: A => B): EMon[B]
  /** This is just a Unit returning map, but is preferred because the method  is explicit that it is called for effects not a value. */
  def forEither(fBad: StrList => Unit, fGood: A => Unit): Unit = fold[Unit](fBad, fGood)
  def flatMap[B](f: A => EMon[B]): EMon[B]
  def getElse[A1 >: A](elseValue: => A1): A1

  /** Use this EMon if good else use other EMmn */
  def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1]
  def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2]
  def toEither: Either[StrList, A] 
  def eitherMap[D](f: A => D): Either[StrList, D]
  def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D]
  def isGood: Boolean
  def isBad: Boolean
}

object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Persist[A]): EMon[A] = thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findType[A]))
    def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Persist](f: A => Unit): Unit = findType[A].foreach(f)
    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] =
      thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findSett[A](settingStr)))
    def findSettingElse[A: Persist](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)
    def findSomeSetting[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(implicit ev: Persist[A]): EMon[A]
    def findSomeSettingElse[A: Persist](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based 
 *  Right[List[String], +A]. */
case class Good[+A](val value: A) extends EMon[A] 
{ def errs: StrList = Nil
  override def map[B](f: A => B): EMon[B] = Good[B](f(value))   
  override def flatMap[B](f: A => EMon[B]): EMon[B] = f(value)
  override def foreach(f: A => Unit): Unit = f(value)
  override def getElse[A1 >: A](elseValue: => A1): A1 = value
  override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A] = this
  override def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
  override def toEither: Either[StrList, A] = Right(value)
  override def eitherMap[D](f: A => D): Either[StrList, D] = Right(f(value))
  override def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D] = f(value)
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
case class Bad[+A](errs: StrList) extends EMon[A]
{ override def map[B](f: A => B): EMon[B] = Bad[B](errs)
  override def flatMap[B](f: A => EMon[B]): EMon[B] = Bad[B](errs)
  override def foreach(f: A => Unit): Unit = {}
  override def getElse[A1 >: A](elseValue: => A1): A1 = elseValue
  override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1] = otherValue
  override def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def toEither: Either[StrList, A] = Left(errs)
  override def eitherMap[D](f: A => D): Either[StrList, D] = Left(errs)
  override def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D] = (Left(errs))
  override def isGood: Boolean = false
  override def isBad: Boolean = true
}

object Bad
{
  implicit def BadShowImplicit[A](implicit ev: Show[A]): Show[Bad[A]] = new Show[Bad[A]] with ShowCompound[Bad[A]]
  {
    override def syntaxDepth: Int = 2
    override def typeStr: String = "Bad" + ev.typeStr.enSquare
    override def showSemi(obj: Bad[A]): String = obj.errs.mkString("; ")
    override def showComma(obj: Bad[A]): String = obj.errs.semiFold// semiColonFold //.mkString(", ")
  }
}

sealed trait EMon2[+A1, +A2]
{ def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B]
}

final case class Good2[A1, A2](a1: A1, a2: A2) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = f(a1, a2)
}

final case class Bad2[A1, A2](errs: StrList) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = Bad[B](errs)
}

sealed trait EMon3[+A1, +A2, +A3]
{ def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B]
}

final case class Good3[A1, A2, A3](a1: A1, a2: A2, a3: A3) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = f(a1, a2, a3)
}

final case class Bad3[A1, A2, A3](errs: StrList) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = Bad[B](errs)
}
