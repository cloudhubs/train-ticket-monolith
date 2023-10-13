package edu.fudanselab.trainticket.service;

import edu.fudanselab.trainticket.util.Response;
import org.springframework.http.HttpHeaders;
import edu.fudanselab.trainticket.entity.RebookInfo;

/**
 * @author fdse
 */
public interface RebookService {
    Response rebook(RebookInfo info, HttpHeaders headers);
    Response payDifference(RebookInfo info,  HttpHeaders headers);
}
