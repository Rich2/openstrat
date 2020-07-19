/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait SquareTr extends Rectangle
{ def height: Double = width
}

/** Companion object for the Square trait. However its apply methods delegate to the SquareClass implementation class. */
object SquareTr
{

}