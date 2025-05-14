/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** The functor type class. */
trait Functor[F[_]]
{ /** Takes an F of A and maps it to an F of B. */
  def mapT[A, B](fa: F[A], f: A => B): F[B]
}

/** Companion object for the [[Functor]] type class, contains implicit instances. */
object Functor
{
  given listEv: Functor[List] = new Functor[List] { override def mapT[A, B](fa: List[A], f: A => B): List[B] = fa.map(f) }

  given vectorEv: Functor[Vector] = new Functor[Vector] { override def mapT[A, B](fa: Vector[A], f: A => B): Vector[B] = fa.map(f) }

  given optionEv: Functor[Option] = new Functor[Option]
  { override def mapT[A, B](fa: Option[A], f: A => B): Option[B] = fa match
    { case None => None
      case Some(a) => Some(f(a))
    }
  }

  given someEv: Functor[Some] = new Functor[Some]
  { override def mapT[A, B](fa: Some[A], f: A => B): Some[B] = Some(f(fa.value))
  }

  given eitherEv[L]: Functor[({type λ[α] = Either[L, α]})#λ] = new Functor[({type λ[α] = Either[L, α]})#λ]
  { override def mapT[A, B](fa: Either[L, A], f: A => B): Either[L, B] = fa.map(f)
  }

  given errBiEv[E <: Throwable]: Functor[({type λ[α] = ErrBi[E, α]})#λ] = new Functor[({type λ[α] = ErrBi[E, α]})#λ]
  { override def mapT[A, B](fa: ErrBi[E, A], f: A => B): ErrBi[E, B] = fa.map(f)    
  }
}

trait Apply[F[_]] extends Functor[F]
{ /** Given a value and a function in the Apply context, applies the function to the value. */
  //def ap[A, B](ff: F[(A) ⇒ B])(fa: F[A]): F[B]

  def map2[A1, A2, B](fa1: F[A1], fa2: F[A2])(f: (A1, A2) => B): F[B]
}