package services;

import com.google.inject.ImplementedBy;
import services.impl.NpcServiceImpl;

/**
 * Created by wxji on 2017-09-18.
 */
@ImplementedBy(NpcServiceImpl.class)
public interface NpcService {
}
