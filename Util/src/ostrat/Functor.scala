package ostrat

trait Functor[F[_]]
{ def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor
{
  implicit val optionImplicit: Functor[Option] = new Functor[Option]
  { def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match
    { case None => None
      case Some(a) => Some(f(a))
    }
  }

  implicit val someImplicit: Functor[Some] = new Functor[Some] {
    override def map[A, B](fa: Some[A])(f: A => B): Some[B] = Some(f(fa.value))
  }
}
