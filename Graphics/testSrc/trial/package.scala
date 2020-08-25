package object trial
{
  implicit def TransToExtension[T](value: T)(implicit ev: Trans[T]) = new TransExtension[T](value, ev)  
}
  