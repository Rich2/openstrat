/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait TellSimple extends TellQuanta
{ /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  final override def tell(style: ShowStyle): String = style match
  { case ShowTyped => typeStr.appendParenth(str)
    case ShowUnderScore => "_"
    case _ => str
  }

  override def syntaxDepth: Int = 1
}


/** Shows a simple object like a Singleton object or a Double. For your own objects that you control it is better to use Show and its helper sub
 * rather than the sub traits of ShowT to implement your Show functionality.S */
trait ShowSimple[-A] extends Show[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showDecT(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match
  { case ShowTyped => typeStr + strT(obj).enParenth
    case ShowUnderScore => "_"
    case _ => strT(obj)
  }
}

/** [[Show]] class for types that extend [[TellSimple]]. */
case class ShowTellSimple[R <: TellSimple](typeStr: String) extends ShowTell[R]