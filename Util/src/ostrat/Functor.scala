package ostrat

trait Functor[F[_]]
{ def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor
{
  implicit def arrImplicit: Functor[Arr] = new Functor[Arr] { override def map[A, B](fa: Arr[A])(f: A => B): Arr[B] = fa.map(f) }

  //implicit def arrayImplicit: Functor[Array] = new Functor[Array] { override def map[A, B](fa: Array[A])(f: A => B): Array[B] = fa.map(f) }

  implicit def listImplicit: Functor[List] = new Functor[List] { override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f) }

  implicit def vectorImplicit: Functor[Vector] = new Functor[Vector] { override def map[A, B](fa: Vector[A])(f: A => B): Vector[B] = fa.map(f) }

  implicit val optionImplicit: Functor[Option] = new Functor[Option]
  { def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match
    { case None => None
      case Some(a) => Some(f(a))
    }
  }

  implicit val someImplicit: Functor[Some] = new Functor[Some] {
    override def map[A, B](fa: Some[A])(f: A => B): Some[B] = Some(f(fa.value))
  }

  /*implicit def eitherImplicit[L]: Functor[Either[L, _]] = new Functor[Either[L, _]]
  { override def map[A, B](fa: Either[L, A])(f: A => B): Either[L, B] = fa.map(f)
  }*/
}
