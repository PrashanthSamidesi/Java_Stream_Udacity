/*
package stream;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SummarizeClients {
    public static void main(String[] args){

        List<UdacisearchClient> clients = ClientStore.getClients();
        int numClients = clients.getSize();

        int totalQuarterlySpend =
                clients
                        .stream()
                        .mapToInt(UdacisearchClient::getQuarterlyBudget)
                        .sum();

        double averageBudget =
                clients
                        .stream()
                        .maptoDouble(UdacisearchClient::getQuarterlyBudget)
                        .average()
                        .orElse(0);

        long nextExpiration =
                clients
                        .stream()
                        .min(Comparator.comparing(UdacisearchClient::getContractEnd))
                        .map(UdacisearchClient::getId)
                        .orElse(-1L);

        List<ZoneId> representedZoneId =
                clients
                        .stream()
                        .flatMap(c -> c.getTimeZones().stream())
                        .distinct()
                        .collect(Collectors.toList());
        Map<Year, Long> contractsPerYear =
                clients
                        .stream()
                        .collect(Collectors.groupingBy(SummarizeClients::getContractYear, Collectors.counting()));


    }

    private static Year getContractYear(UdacisearchClient client){
        LocalDate contractDate =
                LocalDate.ofInstant(client.getContractStart(), client.getTimeZones().get(0));
        return Year.of(contractDate.getYear());
    }
}
*/
