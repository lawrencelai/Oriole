package org.oriole.document.dao;

import org.oriole.document.exception.SequenceException;

public interface SequenceDao {
	long getNextSequenceId(String key) throws SequenceException;
}
