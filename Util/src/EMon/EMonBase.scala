package ostrat
import annotation.unchecked.uncheckedVariance

/** This is the base trait for the specialisations of Error Monads. Each specialisation eg EMonInt has its own specialised Good eg GoodInt and Bad eg
 * BadInt sub classes. It can be Good eg GoodInt containing a value of A eg Int, or Bad eg BadInt containing a Refs of Strings. A special case of Bad
 * is the NoGood object eg NoInt which has not value of type A and no error strings. To avoid boxing most methods are left abstract to be implemented
 * in the leaf classes, to avoid unnecessary boxing on generic functions. */
/*trait EMonBase[+A]
{

}*/

/*
trait GoodBase[+A] extends EMonBase[A]
{
}

trait BadBase[+A] extends EMonBase[A]
{
}*/
