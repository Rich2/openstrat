/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

case class ParseErr(fp: FilePosn, detail: String)

object Good
{
   def apply[B](res: B): Good[B] = Right[Seq[ParseErr], B](res)
   
   def unapply[A](eMon: EMon[A]): Option[A] = eMon match
   {
      case Right(a) => Some(a)
      case _ => None
   }
}

object Bad
{
   def apply[B](errs: Seq[ParseErr]): Bad[B] = Left[Seq[ParseErr], B](errs)
   
   def unapply[A](eMon: EMon[A]): Option[Seq[ParseErr]] = eMon match
   {
      case Left(errs) => Some(errs)
      case _ => None
   }
}