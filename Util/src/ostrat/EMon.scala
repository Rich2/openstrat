/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are advantages to having a
 *  separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the methods biMap, to Either,
 *  eitherMap and eitherFlatMap when interoperability with Either is required. */
sealed trait EMon[+A]
{
  @inline def fold[B](fBad: StrList => B, fGood: A => B): B = this match
  {
    case Good(a) => fGood(a)
    case Bad(errs) => fBad(errs)
  }
  
  def errs: StrList
  def map[B](f: A => B): EMon[B] = fold(Bad[B](_), a => Good[B](f(a)))   
  def flatMap[B](f: A => EMon[B]): EMon[B] = fold(Bad(_), f(_))
  def foreach(f: A => Unit): Unit = fold(errs => {}, f(_))
  def getElse[A1 >: A](elseValue: => A1): A1 = fold[A1](errs => elseValue, a => a)
  def biMap[L2, R2](fLeft: StrList => L2, fRight: A => R2): Either[L2, R2] = fold(errs => Left(fLeft(errs)), a => Right(fRight(a)))
  def toEither: Either[StrList, A] = fold(Left(_), Right(_))
  def eitherMap[D](f: A => D): Either[StrList, D] = fold(Left(_), a => Right(f(a)))
  def eitherFlatMap[D](f: A => Either[StrList, D]): Either[StrList, D] = fold(Left(_), f(_))
   
  def map2[A2, B](eMon2: EMon[A2], f: (A, A2) => B): EMon[B] = this match
  {
    case Good(a) => eMon2.map(a2 => f(a, a2))
    case Bad(errs1) => Bad[B](errs1 ::: eMon2.errs)
  }
   
  def map3[A2, A3, B](eMon2: EMon[A2], eMon3: EMon[A3], f: (A, A2, A3) => B): EMon[B] = this match
  {
    case Bad(errs1) => Bad[B](errs1 ::: eMon2.errs ::: eMon3.errs)
    case Good(a1) => eMon2 match
    {
      case Bad(errs2) => Bad[B](errs2 ::: eMon3.errs)
      case Good(a2) => eMon3.map(a3 => f(a1, a2, a3))        
    }            
  }
 
  def map4[A2, A3, A4, B](eMon2: EMon[A2], eMon3: EMon[A3], eMon4: EMon[A4], f: (A, A2, A3, A4) => B): EMon[B] = this match
  {
    case Bad(errs1) => Bad[B](errs1 ::: eMon2.errs ::: eMon3.errs ::: eMon4.errs)
    case Good(a1) => eMon2 match
    {
      case Bad(errs2) => Bad[B](errs2 ::: eMon3.errs ::: eMon4.errs)
      case Good(a2) => eMon3 match
      {
        case Bad(errs3) => Bad[B](errs3 ::: eMon4.errs)
        case Good(a3) =>eMon4.map(a4 => f(a1, a2, a3, a4))        
      }
    }            
  }
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based 
 *  Right[List[String], +A]. */
case class Good[+A](val value: A) extends EMon[A] 
{
  def errs: StrList = Nil
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
case class Bad[+A](errs: StrList) extends EMon[A]// = Left[List[ParseErr], B]

object ParseErr
{
  def apply (fp: FilePosn, detail: String): String = detail
}
