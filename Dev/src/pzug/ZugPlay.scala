/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug

case class ZPlayer(polities: Arr[Polity])

object PlayBritain extends ZPlayer (Arr(Britain))
object PlayGermany extends ZPlayer (Arr(Germany))
object PlayGermanyBritain extends ZPlayer (Arr(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (Arr(Germany, France))