/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, java.net.*, java.io.{ BufferedReader, InputStreamReader }

object ServRaw extends App
{ deb("Starting")
  val servSock: ServerSocket = new ServerSocket(8080)
  var numConns: Int = 0
  while(true)
  { val sock: Socket = servSock.accept()
    val conn = new ConnSesh(numConns, sock)
    conn.run()
    numConns += 1
  }
  servSock.close()
  deb("Finishing")
}

class ConnSesh(val cNum: Int, val sock: Socket) extends Runnable
{
  override def run(): Unit =
  { val readbuf: BufferedReader = new BufferedReader(new java.io.InputStreamReader(sock.getInputStream()))
    var line: String = null
    val acc = StringBuff()
    while ( { line = readbuf.readLine; line != null && line != "" })
    { acc.grow(line)
      println(line)
    }
    val req = HttpReq(acc)
    req match{
      case Good(hrg: HttpReqGet) => hrg.uri match
      { case "/" => sock.getOutputStream.write(IndexPage.httpRespBytes("localhost"))
        case _ => deb("Other URI")
      }
      case _ => deb("Other match")
    }
    readbuf.close
  }
}