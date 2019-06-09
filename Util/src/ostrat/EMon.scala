/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are advantages to having a
 *  separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the methods biMap, to Either,
 *  eitherMap and eitherFlatMap when interoperability with Either is required. Have forgotten the reason I chose a separate class over using Either.*/
sealed trait EMon[+A]
{
  @inline def fold[B](fBad: StrList => B, fGood: A => B): B = this match
  {
    case Good(a) => fGood(a)
    case Bad(errs) => fBad(errs)
  }
  
  def errs: StrList
  def map[B](f: A => B): EMon[B]  
  def flatMap[B](f: A => EMon[B]): EMon[B]
  def foreach(f: A => Unit): Unit
  def getElse[A1 >: A](elseValue: => A1): A1
  def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1]
  def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2]
  def toEither: Either[StrList, A] 
  def eitherMap[D](f: A => D): Either[StrList, D]
  def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D]   
  def map2[A2, B](eMon2: EMon[A2], f: (A, A2) => B): EMon[B]
  def map3[A2, A3, B](eMon2: EMon[A2], eMon3: EMon[A3], f: (A, A2, A3) => B): EMon[B]
  def map4[A2, A3, A4, B](eMon2: EMon[A2], eMon3: EMon[A3], eMon4: EMon[A4], f: (A, A2, A3, A4) => B): EMon[B]
  def isGood: Boolean
  def isBad: Boolean
}

object EMon
{
  implicit class EMonStringImplicit(thisEMon: EMon[String])
  { def findType[A](implicit ev: Persist[A]): EMon[A] = thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findType[A]))
    def findTypeElse[A: Persist](elseValue: => A): A = findType[A].getElse(elseValue)
    def findTypeForeach[A: Persist](f: A => Unit): Unit = findType[A].foreach(f)
    def findSett[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] =
      thisEMon.flatMap(str => pParse.stringToStatements(str).flatMap(_.findSett[A](settingStr)))
    def findSettingElse[A: Persist](settingStr: String, elseValue: => A): A = findSett[A](settingStr).getElse(elseValue) 
  }
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based 
 *  Right[List[String], +A]. */
case class Good[+A](val value: A) extends EMon[A] 
{
  def errs: StrList = Nil
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
  override def map2[A2, B](eMon2: EMon[A2], f: (A, A2) => B): EMon[B] = eMon2.map(a2 => f(value, a2))
  
  override def map3[A2, A3, B](eMon2: EMon[A2], eMon3: EMon[A3], f: (A, A2, A3) => B): EMon[B] = eMon2 match
  {
    case Bad(errs2) => Bad[B](errs2 ::: eMon3.errs)
    case Good(a2) => eMon3.map(a3 => f(value, a2, a3))                
  }
 
  override def map4[A2, A3, A4, B](eMon2: EMon[A2], eMon3: EMon[A3], eMon4: EMon[A4], f: (A, A2, A3, A4) => B): EMon[B] = eMon2 match
  {
    case Bad(errs2) => Bad[B](errs2 ::: eMon3.errs ::: eMon4.errs)
    case Good(a2) => eMon3 match
    {
      case Bad(errs3) => Bad[B](errs3 ::: eMon4.errs)
      case Good(a3) =>eMon4.map(a4 => f(value, a2, a3, a4))        
    }
  }  
}

object Good
{
  implicit def GoodShowImplicit[A](implicit ev: Show[A]): ShowOnly[Good[A]] = new ShowOnly[Good[A]] with ShowCompound[Good[A]]
  {
    override def syntaxDepth: Int = ev.syntaxDepth + 1
    override def typeStr: String = "Good" + ev.typeStr.enSquare
    override def showSemi(obj: Good[A]): String = ev.showSemi(obj.value)
    override def showComma(obj: Good[A]): String = ev.showComma(obj.value)
  }
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
case class Bad[+A](errs: StrList) extends EMon[A]
{
  override def map[B](f: A => B): EMon[B] = Bad[B](errs)   
  override def flatMap[B](f: A => EMon[B]): EMon[B] = Bad(errs)
  override def foreach(f: A => Unit): Unit = {}
  override def getElse[A1 >: A](elseValue: => A1): A1 = elseValue
  override def elseTry[A1 >: A](otherValue: EMon[A1]): EMon[A1] = otherValue
  override def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def toEither: Either[StrList, A] = Left(errs)
  override def eitherMap[D](f: A => D): Either[StrList, D] = Left(errs)
  override def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D] = (Left(errs))
  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def map2[A2, B](eMon2: EMon[A2], f: (A, A2) => B): EMon[B] = Bad[B](errs ::: eMon2.errs)   
  override def map3[A2, A3, B](eMon2: EMon[A2], eMon3: EMon[A3], f: (A, A2, A3) => B): EMon[B] = Bad[B](errs ::: eMon2.errs ::: eMon3.errs) 
  
  override def map4[A2, A3, A4, B](eMon2: EMon[A2], eMon3: EMon[A3], eMon4: EMon[A4], f: (A, A2, A3, A4) => B): EMon[B] = 
    Bad[B](errs ::: eMon2.errs ::: eMon3.errs ::: eMon4.errs)    
}

object Bad
{
  implicit def BadShowImplicit[A](implicit ev: Show[A]): ShowOnly[Bad[A]] = new ShowOnly[Bad[A]] with ShowCompound[Bad[A]]
  {
    override def syntaxDepth: Int = 2
    override def typeStr: String = "Bad" + ev.typeStr.enSquare
    override def showSemi(obj: Bad[A]): String = obj.errs.mkString("; ")
    override def showComma(obj: Bad[A]): String = obj.errs.semiFold// semiColonFold //.mkString(", ")
  }
}
