package ostrat

trait Opt[A] extends Any
{
   def foreach(f: A => Unit): Unit
}

class OptRef[+A <: AnyRef](val value: A) extends AnyVal
{
   def foreach(f: A => Unit): Unit = if(value != null) f(value)
   @inline def empty: Boolean = value != null
   override def toString: String = if(value == null) "NoOpt" else "Some" + value.toString.enParenth
}

trait OptInt extends Opt[Int]

case class SomeInt(value: Int) extends OptInt
{ override def foreach(f: Int => Unit): Unit = f(value)
  //override def toString: String = "Some" + value.toString.enparenth
}

case object NoInt extends OptInt
{ override def foreach(f: Int => Unit): Unit = {}
}
