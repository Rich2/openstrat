package ostrat

sealed trait EMon2[+A1, +A2]
{ def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B]
  //def flatMapRefs[B <: AnyRef](f: (A1, A2) => EMonRefsSpec[B]): ERefsSpec[B]
  def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2]
  def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2]
}

final case class Good2[A1, A2](a1: A1, a2: A2) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = f(a1, a2)
 // override def flatMapRefs[B <: AnyRef](f: (A1, A2) => ERefsSpec[B]): ERefsSpec[B] = f(a1, a2)
  override def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2] = f(a1, a2)
  override def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2] = Good2[B1, A2](f(a1), a2)
}

final case class Bad2[A1, A2](val errs: Strings) extends EMon2[A1, A2]
{ override def flatMap[B](f: (A1, A2) => EMon[B]): EMon[B] = Bad[B](errs)
//  override def flatMapRefs[B <: AnyRef](f: (A1, A2) => ERefsSpec[B]): ERefsSpec[B] = BadRefsSpec[B](errs)
  override def a1Map[B1](f: (A1 => B1)): EMon2[B1, A2] = Bad2[B1, A2](errs)
  override def flatMap2[B1, B2](f: (A1, A2) => EMon2[B1, B2]): EMon2[B1, B2] = Bad2[B1, B2](errs)
}

sealed trait EMon3[+A1, +A2, +A3]
{ def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B]
 // def flatMapRefs[B <: AnyRef](f: (A1, A2, A3) => ERefsSpec[B]): ERefsSpec[B]
}

final case class Good3[+A1, +A2, +A3](a1: A1, a2: A2, a3: A3) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = f(a1, a2, a3)
  //override  def flatMapRefs[B <: AnyRef](f: (A1, A2, A3) => ERefsSpec[B]): ERefsSpec[B] = f(a1, a2, a3)
}

final class Bad3[A1, A2, A3](val errs: Strings) extends EMon3[A1, A2, A3]
{ override def flatMap[B](f: (A1, A2, A3) => EMon[B]): EMon[B] = Bad[B](errs)
  //override  def flatMapRefs[B <: AnyRef](f: (A1, A2, A3) => ERefsSpec[B]): ERefsSpec[B] = BadRefsSpec[B](errs)
}