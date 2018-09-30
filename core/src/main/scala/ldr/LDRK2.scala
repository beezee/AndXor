package ldr
import scala.language.higherKinds
import scalaz.{Apply, Monoid, \/}
import scalaz.Id.Id
import scalaz.syntax.either._

trait LDRK2[F[_], A1, A2] extends LDR {
  type Prod = (F[A1], F[A2])
  type Cop = (F[A1] \/ F[A2])
  def combine[G[_]](implicit a0: G[F[A1]], a1: G[F[A2]]): ComposeLDR[G, Cop, Prod] =
    new ComposeLDR[G, Cop, Prod] {
      def mkChoose[B](f: B => Cop)(implicit d: Decidable[G]): G[B] =
        Combine.choose2(a0, a1)(f)
      def mkAlt[B](f: Cop => B)(implicit a: Alt[G]): G[B] =
        Combine.altly2(a0, a1)(f)
      def mkDivide[B](f: B => Prod)(implicit d: Divide[G]): G[B] =
        Combine.divide2(a0, a1)(f)
      def mkApply[B](f: Prod => B)(implicit a: Apply[G]): G[B] =
        Combine.apply2(a0, a1)((i0, i1) => f((i0, i1)))
    }

  object instances {

    implicit val inja0: Inj[Cop, F[A1]] =
      Inj.instance(_.left[F[A2]])

    implicit val inja1: Inj[Cop, F[A2]] =
      Inj.instance(_.right[F[A1]])

    implicit def lifta0(implicit M: Monoid[Prod]): Inj[Prod, F[A1]] = {
      val (_, a0) =
        M.zero
      Inj.instance((_, a0))
    }

    implicit def lifta1(implicit M: Monoid[Prod]): Inj[Prod, F[A2]] = {
      val (a0, _) =
        M.zero
      Inj.instance((a0, _))
    }

  }

  import instances._

  val injEv = combine[Inj.Aux[Cop]#Out].choose
  def liftEv(implicit M: Monoid[Prod]): Inj[Prod, Prod] = combine[Inj.Aux[Prod]#Out].divide

}

object LDRK2 {

  def apply[F[_], A1, A2]: LDRK2[F, A1, A2] =
    new LDRK2[F, A1, A2] {}
}

trait LDRF2[A1, A2] {
  type Repr[F[_]] = LDRK2[F, A1, A2]
  def apply[F[_]]: Repr[F] =
    new LDRK2[F, A1, A2] {}
}

object LDRF2 {
  def apply[A1, A2]: LDRF2[A1, A2] =
    new LDRF2[A1, A2] {}
}

trait LDR2[A1, A2] extends LDRK2[Id, A1, A2]

object LDR2 {
  def apply[A1, A2]: LDR2[A1, A2] =
    new LDR2[A1, A2] {}
}