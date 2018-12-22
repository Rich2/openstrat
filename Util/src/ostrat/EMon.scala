/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

sealed trait EMon[+A] //= Either[List[ParseErr], B]
{
  @inline def fold[B](fBad: List[String] => B, fGood: A => B): B = this match
  {
    case Good(a) => fGood(a)
    case Bad(errs) => fBad(errs)
  }
  
   def errs: StrList// = fold(l => l, a => Nil)
   
   def map[B](f: A => B): EMon[B] = fold(Bad[B](_), a => Good[B](f(a)))   
   def flatMap[B](f: A => EMon[B]): EMon[B] = fold(Bad[B](_), f(_))
   def foreach(f: A => Unit): Unit = fold(errs => {}, f(_))
   def getElse[A1 >: A](elseValue: => A1): A1 = fold[A1](errs => elseValue, a => a)
   
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

/** The Good case of EMon[+A] */
case class Good[+A](val value: A) extends EMon[A] 
{
  def errs: StrList = Nil
}

/** The errors case of EMon[+A] */
case class Bad[+A](errs: StrList) extends EMon[A]// = Left[List[ParseErr], B]

object ParseErr
{
  def apply (fp: FilePosn, detail: String): String = detail
}

//object Good
//{ def apply[B](res: B): Good[B] = Right[List[ParseErr], B](res)
//   
//  def unapply[A](eMon: EMon[A]): Option[A] = eMon match
//  { case Right(a) => Some(a)
//    case _ => None
//  }
//}
//
//object Bad
//{
//   def apply[B](errs: List[ParseErr]): Bad[B] = Left[List[ParseErr], B](errs)
//   
//   def unapply[A](eMon: EMon[A]): Option[List[ParseErr]] = eMon match
//   { case Left(errs) => Some(errs)
//     case _ => None
//   }
//}