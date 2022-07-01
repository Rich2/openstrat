/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Specialised [[EMon]] for values of 2 types, instead of using EMon[(A, B)]. */
sealed trait EMon2[+A1, +A2]
{ def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B]
  def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2]
  def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2]
}

/** Specialised [[Good]] for values of 2 types, instead of using Good[(A, B)]. */
final case class Good2[A1, A2](a1: A1, a2: A2) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = f(a1, a2)
  override def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2] = f(a1, a2)
  override def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2] = Good2[B1, A2](f(a1), a2)
}

/** Specialised [[Bad]] for values of 2 types, instead of using Bad[(A, B)]. */
final case class Bad2[A1, A2](val errs: StringArr) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = Bad[B](errs)
  override def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2] = Bad2[B1, A2](errs)
  override def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2] = Bad2[B1, B2](errs)
}

/** Specialised [[EMon]] for values of 3 types, instead of using EMon[(A, B, C)]. */
sealed trait EMon3[+A1, +A2, +A3]
{ def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B]
}

/** Specialised [[Good]] for values of 3 types, instead of using Good[(A, B, C)]. */
final case class Good3[+A1, +A2, +A3](a1: A1, a2: A2, a3: A3) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = f(a1, a2, a3)
}

/** Specialised [[Bad]] for values of 3 types, instead of using Bad[(A, B, C)]. */
final class Bad3[A1, A2, A3](val errs: StringArr) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = Bad[B](errs)
}

/** Companion object for the [[Bad3]] trait, contains factory apply and unapply methods. */
object Bad3
{ def apply[A1, A2, A3](errs: StringArr): Bad3[A1, A2, A3] = new Bad3[A1, A2, A3](errs)

  def unapplySeq(inp: Any): Option[Seq[String]] = inp match {
    case b: Bad3[_, _, _] => Some(b.errs.toList)
    case _ => None
  }
}