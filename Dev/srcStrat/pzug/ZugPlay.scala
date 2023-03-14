/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug

case class ZPlayer(polities: RArr[Polity])

object PlayBritain extends ZPlayer (RArr(Britain))
object PlayGermany extends ZPlayer (RArr(Germany))
object PlayGermanyBritain extends ZPlayer (RArr(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (RArr(Germany, France))