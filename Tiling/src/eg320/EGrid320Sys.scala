/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._

trait EGrid320Sys extends EGridSys
{ override val cScale: Length = 80.kMetres
}

trait EGrid320MainMulti extends EGridMainMulti with EGrid320Sys
{ override def hcDelta: Int = 1024
}