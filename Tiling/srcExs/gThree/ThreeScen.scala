/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._

sealed trait Terr
object Water extends Terr
object Woods extends Terr
object Plain extends Terr

trait ThreeScen extends HexGridScen
{

}