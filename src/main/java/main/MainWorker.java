package main;

import actors.listeners.ClusterListener;
import actors.listeners.MetricsListener;
import actors.worker.WorkerRegion;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MainWorker {

    public static void main(String[] args) {

        final Config config = ConfigFactory.load("worker");
        String clusterName = config.getString("clustering.cluster.name");
        String port = config.getString("clustering.port");

        ActorSystem system = ActorSystem.create(clusterName, config);

        Cluster.get(system)
                .registerOnMemberUp(
                        () -> {
                            system.actorOf(Props.create(ClusterListener.class), "cluster-listener-worker");

                            final ActorRef clusterClient =
                                    system.actorOf(
                                            ClusterClient.props(
                                                    ClusterClientSettings.create(system)),
                                            "clusterClient");

                            // The Id of the worker region will be the port
                            system.actorOf(WorkerRegion.props(port, clusterClient), "workerRegion");
                            system.actorOf(Props.create(MetricsListener.class), "metricsListener");
                        });
    }
}
