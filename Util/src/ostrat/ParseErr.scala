/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

case class ParseErr(fp: FilePosn, detail: String)

object Good
{ def apply[B](res: B): Good[B] = Right[List[ParseErr], B](res)
   
  def unapply[A](eMon: EMon[A]): Option[A] = eMon match
  { case Right(a) => Some(a)
    case _ => None
  }
}

object Bad
{
   def apply[B](errs: List[ParseErr]): Bad[B] = Left[List[ParseErr], B](errs)
   
   def unapply[A](eMon: EMon[A]): Option[List[ParseErr]] = eMon match
   { case Left(errs) => Some(errs)
     case _ => None
   }
}