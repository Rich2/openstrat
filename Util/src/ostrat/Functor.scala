package ostrat

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
object Functor
{
// Example implementation for Option
  implicit val functorForOption: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case None => None
      case Some(a) => Some(f(a))
    }
  }
}
