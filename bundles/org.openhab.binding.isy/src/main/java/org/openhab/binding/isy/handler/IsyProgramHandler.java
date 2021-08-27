package org.openhab.binding.isy.handler;

import org.openhab.binding.isy.IsyBindingConstants;
import org.openhab.binding.isy.config.IsyProgramConfiguration;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.types.Command;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsyProgramHandler extends AbtractIsyThingHandler implements IsyThingHandler {
    private static final Logger logger = LoggerFactory.getLogger(IsyProgramHandler.class);

    public IsyProgramHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        IsyProgramConfiguration var_config = getThing().getConfiguration().as(IsyProgramConfiguration.class);
        if (IsyBindingConstants.CHANNEL_PROGRAM_CONTROL.equals(channelUID.getId())) {
            getBridgeHandler().getInsteonClient().changeProgramState(var_config.id, command.toString());
            updateState(channelUID, UnDefType.UNDEF);
            return;
        }
        if (command instanceof OnOffType) {
            getBridgeHandler().getInsteonClient().changeProgramState(var_config.id, channelUID.getId());
        } else {
            logger.warn("Unsupported command for variable handleCommand: " + command.toFullString());
        }
    }

    @Override
    public void handleUpdate(String control, String action, String node) {
        // TODO Auto-generated method stub
        logger.warn("Must handle update for program");
    }

    @Override
    public void initialize() {
        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }
}
