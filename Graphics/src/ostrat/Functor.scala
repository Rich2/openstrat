/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** The functor type class. */
trait Functor[F[_]]
{ /** Takes an F of A and maps it to an F of B. */
  def mapT[A, B](fa: F[A], f: A => B): F[B]
}

/** Companion object for the [[Functor]] type class, contains implicit instances. */
object Functor
{
  implicit def eMonImplicit: Functor[EMon] = new Functor[EMon] { override def mapT[A, B](fa: EMon[A], f: A => B): EMon[B] = fa.map(f) }

  implicit def listImplicit: Functor[List] = new Functor[List] { override def mapT[A, B](fa: List[A], f: A => B): List[B] = fa.map(f) }

  implicit def vectorImplicit: Functor[Vector] = new Functor[Vector] { override def mapT[A, B](fa: Vector[A], f: A => B): Vector[B] = fa.map(f) }

  implicit val optionImplicit: Functor[Option] = new Functor[Option]
  { override def mapT[A, B](fa: Option[A], f: A => B): Option[B] = fa match
    { case None => None
      case Some(a) => Some(f(a))
    }
  }

  implicit val someImplicit: Functor[Some] = new Functor[Some] {
    override def mapT[A, B](fa: Some[A], f: A => B): Some[B] = Some(f(fa.value))
  }

  implicit def eitherImplicit[L]: Functor[({type λ[α] = Either[L, α]})#λ] = new Functor[({type λ[α] = Either[L, α]})#λ]
  { override def mapT[A, B](fa: Either[L, A], f: A => B): Either[L, B] = fa.map(f)
  }
}